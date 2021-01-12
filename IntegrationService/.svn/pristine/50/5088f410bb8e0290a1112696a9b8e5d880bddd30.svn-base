package biz.digitalhouse.integration.v3.services.jobs;

import biz.digitalhouse.integration.v3.services.technicalBreak.TBManager;
import biz.digitalhouse.integration.v3.services.transactions.PlayTransactionJobManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Babenko
 *         on 19.04.2016.
 */
@Service
public class WinJob implements Job {

    private final Log log = LogFactory.getLog(getClass());
    @Autowired
    protected PlayTransactionJobManager playTransactionJobManager;
    @Autowired
    private TBManager technicalBreak;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(technicalBreak.isTechnicalBreak()) {
            if(log.isDebugEnabled()) {
                log.debug("Technical break. Job disabled.");
            }
            return;
        }

        playTransactionJobManager.win();
    }
}
