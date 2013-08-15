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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.ConceptMap;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.GlobalProperty;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.Privilege;
import org.openmrs.RelationshipType;
import org.openmrs.Role;
import org.openmrs.VisitType;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.UserService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.rheapocadapter.impl.TransactionServiceImpl;
import org.openmrs.module.rheapocadapter.transaction.Transaction;
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
		FlushQueuesResult fqr = new FlushQueuesResult();
		
		//List<Transaction> queues = TransactionServiceImpl.getAllQueue(null);
		
    	return fqr;
	}
    

}
