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
import org.openmrs.module.rheapocadapterflushqueues.api.RHEAPoCAdapterFlushQueuesService;
import org.openmrs.module.rheapocadapterflushqueues.FlushQueuesResult;
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
			log.info("Attempting to flush queues.");
			cleanup.results = fqs.flushQueues();
		} catch (UnexpectedRollbackException ex) {
			log.info("Failed to flush queues: ", ex);
			cleanup.results.setStatus(false);
			if (cleanup.results==null){
				cleanup.results = new FlushQueuesResult();
			}
		}
		
		return new ModelAndView("redirect:flushQueues.form");
	}

	public static class CleanUp {
		private Boolean status = null;
		
		private FlushQueuesResult results = null;
		
		public Boolean getStatus(){
			if(results == null){
				return null;
			}
			else {
				return results.getStatus();
			}
		}
		
		public FlushQueuesResult getResults() {
			return results;
		}
		
		public void setResults(FlushQueuesResult results){
			this.results = results;
		}

	}
	
}
