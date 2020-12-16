package uk.ac.ucl.twitter.search.geo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.ScheduleExpression;
import jakarta.ejb.Singleton;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import jakarta.inject.Inject;
import uk.ac.ucl.twitter.search.geo.client.SearchClient;
import uk.ac.ucl.twitter.search.geo.persistence.EntityAccess;
import uk.ac.ucl.twitter.search.geo.persistence.ScheduleAppLocationEntity;

@Singleton
public final class Scheduler {

  /**
   * EJB container-managed service used to invoke #callPublisher at a
   * specific time, as configured in #init.
   */
  @Resource
  private TimerService timerService;

  /**
   * EJB facede to methods for calling JPA entities.
   */
  @EJB
  private EntityAccess entityAccess;

  /**
   * Twitter Search API client.
   */
  @Inject
  private SearchClient searchClient;

  /**
   * EJB life cycle method used for configuring the timer service.
   */
  @PostConstruct
  public void init() {
    for (ScheduleAppLocationEntity scheduleAppLocationEntity : entityAccess
      .findAllScheduleAppLocationEntity()) {
      ScheduleExpression scheduleExpression = new ScheduleExpression();
      scheduleExpression.minute(scheduleAppLocationEntity.getIntervalMinutes());
      TimerConfig timerConfig = new TimerConfig();
      timerConfig.setInfo(
        new AppLocationTimerConfig(
          scheduleAppLocationEntity.getLocation(),
          scheduleAppLocationEntity.getAppName()
        )
      );
      timerService.createCalendarTimer(scheduleExpression, timerConfig);
    }
  }

  /**
   * Calls the Twitter Search API client once the timer service is triggered.
   * @param timer EJB Timer
   */
  @Timeout
  public void callSearchClient(final Timer timer) {
    AppLocationTimerConfig appConfig = (AppLocationTimerConfig) timer.getInfo();
    searchClient.runSearch(
      appConfig.getLocation(), appConfig.getApplicationName()
    );
  }

  /**
   * Closes writing of files and transfers them onto network storage.
   */
  @Schedule(minute = "13", hour = "2")
  public void closeAndTransferFiles() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  /**
   * Compresses files using LZO algorithm.
   */
  @Schedule(minute = "13", hour = "3")
  public void lzoFiles() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  /*
  @Schedule(minute = "11", hour = "2")
  public void dailyCountReport() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }
  */

  /**
   * Transfers monthly file collection onto resilient storage.
   */
  @Schedule(minute = "13", hour = "4", dayOfMonth = "2")
  public void monthlyBackup() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}
