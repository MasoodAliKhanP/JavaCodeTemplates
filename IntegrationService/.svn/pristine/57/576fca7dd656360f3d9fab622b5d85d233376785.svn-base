package biz.digitalhouse.integration.v3.services.technicalBreak;

import biz.digitalhouse.integration.v3.dao.TechnicalBreakDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Vitalii Babenko
 *         on 04.08.2016.
 */
@Service
public class TBManager {

    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private TechnicalBreakDAO technicalBreakDAO;
    private AtomicReference<Calendar> nextUpdate = new AtomicReference<>(Calendar.getInstance());
    private AtomicBoolean result = new AtomicBoolean(false);



    public boolean isTechnicalBreak() {
        if((nextUpdate.get()).before(Calendar.getInstance())) {
            if(log.isDebugEnabled()) {
                log.debug("Update tech break status");
            }

            Calendar calendar = nextUpdate.get();
            calendar.add(Calendar.MINUTE, 1);
            nextUpdate.set(calendar);
            result.set(technicalBreakDAO.isTechnicalBreak());
        }

        return this.result.get();
    }
}
