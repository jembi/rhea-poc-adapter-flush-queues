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
package org.openmrs.module.rheapocadapterflushqueues.web.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.rheapocadapterflushqueues.FlushQueuesResult;
import org.openmrs.module.rheapocadapterflushqueues.api.RHEAPoCAdapterFlushQueuesService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * The main controller.
 */
@Controller
@SessionAttributes("cleanup")
public class  RHEAPoCAdapterFlushQueuesController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/rheapocadapterflushqueues/flushQueues", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		if (model.get("cleanup")==null)
			model.put("cleanup", new CleanUp());
	}
	
	@RequestMapping(value = "/module/rheapocadapterflushqueues/flushQueues", method = RequestMethod.POST)
	public ModelAndView runConfigurator(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("cleanup") CleanUp cleanup, BindingResult errors) {
		RHEAPoCAdapterFlushQueuesService fqs = Context.getService(RHEAPoCAdapterFlushQueuesService.class);
		
		try {
			//cleanup.result = fqs.flushQueues();
			cleanup.status = true;
			cleanup.count = 100;
		} catch (UnexpectedRollbackException ex) {
			cleanup.status = false;
		}
		
		
		/*
		try {
			cleanup.statusAuthTest = cs.performAuthenticationTest(cleanup.getAuthTestInput());
		} catch (UnexpectedRollbackException ex) {
			cleanup.statusAuthTest = false;
		}
		try {
			cleanup.formsResult = cs.validateFormConcepts();
		} catch (UnexpectedRollbackException ex) {
			if (cleanup.formsResult==null)
				cleanup.formsResult = new ValidateFormsResult();
			cleanup.formsResult.setStatus(false);
		}
		*/
		
		return new ModelAndView("redirect:flushQueues.form");
	}
	
	public static class CleanUp {
		private Boolean status;
		private int count;
		private FlushQueuesResult result;
		
		public Boolean getStatus(){
			return status;
		}
		
		public FlushQueuesResult getResult(){
			return result;
		}
		
		public int getCount(){
			return count;
		}
		
	}
}