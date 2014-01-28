/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.schedulerapp.batchjob;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Timer;

/**
 * 
 * @author Christopher Lam
 */
@Local
public interface BatchJobInterface {

	static Logger logger = Logger.getLogger("interface		BatchJobA");

	public void executeJob(Timer timer);
}
