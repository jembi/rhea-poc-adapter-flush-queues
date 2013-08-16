/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.rheapocadapterflushqueues.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.rheapocadapter.impl.TransactionServiceImpl;
import org.openmrs.module.rheapocadapter.service.TransactionService;
import org.openmrs.module.rheapocadapter.handler.EnteredHandler;
import org.openmrs.module.rheapocadapter.transaction.Transaction;
import org.openmrs.module.rheapocadapter.transaction.ArchiveTransaction;
import org.openmrs.module.rheapocadapter.transaction.ErrorTransaction;
import org.openmrs.module.rheapocadapter.transaction.ProcessingTransaction;
import org.openmrs.module.rheapocadapterflushqueues.FlushQueuesResult;
import org.openmrs.module.rheapocadapterflushqueues.api.RHEAPoCAdapterFlushQueuesService;
import org.openmrs.module.rheapocadapterflushqueues.api.db.RHEAPoCAdapterFlushQueuesDAO;

/**
 * It is a default implementation of {@link RHEAPoCConfiguratorService}.
 */
public class RHEAPoCAdapterFlushQueuesServiceImpl extends BaseOpenmrsService implements RHEAPoCAdapterFlushQueuesService {
    
    protected final Log log = LogFactory.getLog(this.getClass());
    
    private RHEAPoCAdapterFlushQueuesDAO dao;
    
    /**
     * @param dao the dao to set
     */
    public void setDao(RHEAPoCAdapterFlushQueuesDAO dao) {
        this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public RHEAPoCAdapterFlushQueuesDAO getDao() {
        return dao;
    }

    @Override
    public FlushQueuesResult flushQueues() {
        
        int ttlArch=0, ttlProc=0, ttlErr=0, cntArch=0, cntProc=0, cntErr=0;
        
        FlushQueuesResult fqr = new FlushQueuesResult();
        
        EnteredHandler enteredHandler = new EnteredHandler();
        
        // Get list of archive transactions in the queue
        List<ArchiveTransaction> archiveTransactions = new ArrayList<ArchiveTransaction>();
        archiveTransactions = (List<ArchiveTransaction>) enteredHandler
                .getArchiveQueue();     
        
        // Get list of processing transactions in the queue
        List<ProcessingTransaction> processingTransactions = new ArrayList<ProcessingTransaction>();
        processingTransactions = (List<ProcessingTransaction>) enteredHandler
                .getProcessingQueue();
        
        // Get list of error transactions in the queue
        List<ErrorTransaction> errorTransactions = (List<ErrorTransaction>) enteredHandler
                .getErrorQueue();
        
        log.info("Flushing transactions");
        // flush archive transactions containing patients not in local database
        ttlArch = archiveTransactions.size();
        for(ArchiveTransaction archTrans : archiveTransactions){
            if(!validPatient(archTrans)){
                enteredHandler.getQueueService().removeQueue(archTrans);
                cntArch++;
            }
        }
        
        // flush processing transactions containing patients not in local database
        ttlProc = processingTransactions.size();
        for(ProcessingTransaction procTrans : processingTransactions){
            if(!validPatient(procTrans)){
                enteredHandler.getQueueService().removeQueue(procTrans);
                cntProc++;
            }
        }
        
        // flush error transactions containing patients not in local database
        ttlErr = errorTransactions.size();
        for(ErrorTransaction errTrans : errorTransactions){
            if(!validPatient(errTrans)){
                enteredHandler.getQueueService().removeQueue(errTrans);
                cntErr++;
            }
        }
        
        log.error(""+ ttlArch + ", " + ttlProc + ", " + ttlErr + ".");

        
        fqr.setCountArchive(cntArch);
        fqr.setCountProcessing(cntProc);
        fqr.setCountError(cntErr);
        fqr.setTotalArchive(ttlArch);
        fqr.setTotalProcessing(ttlProc);
        fqr.setTotalError(ttlErr);
        fqr.setStatus(true);
        
        return fqr;
    }
    
    // check to see if patient in Transaction exists in the database
    // method adapted from org.openmrs.module.rheapocadapter.handler.EnteredHandler.getPatientFromTransaction()
    private Boolean validPatient(Transaction transaction){
         PatientService patService = Context.getPatientService();
         
         String idInMessage = transaction.getMessage().trim();

         if (idInMessage.contains("SavePatientId=") || (idInMessage.contains("UpdatePatientId="))) {
             idInMessage = idInMessage.split("=")[1];
             idInMessage = idInMessage.trim();
             
             // if patient does not exist in local db, return false
             if(patService.getPatient(Integer.parseInt(idInMessage)) == null ||
                     patService.getPatient(Integer.parseInt(idInMessage)).equals(null)){
                return false;
             }
         } else {
             idInMessage = idInMessage.replaceAll(",", "");
             idInMessage = idInMessage.replace("Succeded", "").trim();
             if (idInMessage.startsWith("Saving")) {
                 log.info(idInMessage + " ");
                 String[] idSplited = idInMessage.split(" ");
                 idInMessage = idSplited[idSplited.length - 1];
                 idInMessage = idInMessage.trim();
                 if (idInMessage.startsWith("Id")) {
                     idInMessage = idInMessage.substring(2);
                     if (idInMessage.startsWith("s")) {
                         idInMessage = idInMessage.substring(1);
                     }
                     
                     // if patient does not exist in local db, return false
                     if(patService.getPatient(Integer.parseInt(idInMessage)) == null ||
                             patService.getPatient(Integer.parseInt(idInMessage)).equals(null)){
                        return false;
                     }

                 }else if(idInMessage.startsWith("PatientId")){
                     idInMessage = idInMessage.split("=")[1];
                     idInMessage = idInMessage.trim();
                     
                     // if patient does not exist in local db, return false
                     if(patService.getPatient(Integer.parseInt(idInMessage)) == null ||
                             patService.getPatient(Integer.parseInt(idInMessage)).equals(null)){
                        return false;
                     }

                 }

             } else if ((idInMessage.startsWith("UpdatePatientId="))) {

                 log.info(idInMessage + " ");
                 idInMessage = idInMessage.split("=")[1];
                 idInMessage = idInMessage.trim();
                 
                 // if patient does not exist in local db, return false
                 if(patService.getPatient(Integer.parseInt(idInMessage)) == null ||
                         patService.getPatient(Integer.parseInt(idInMessage)).equals(null)){
                    return false;
                 }
             }
         }
        return true;
    }
}
