package uk.ac.ucl.twitter.search.geo;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

@Singleton
public class Scheduler {

  /**
   * EJB container-managed service used to invoke #callPublisher at a
   * specific time, as configured in #init.
   */
  @Resource
  private TimerService timerService;

  @EJB
  private EntityAccess entityAccess;

  @Inject
  private SearchClient searchClient;

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

  @Timeout
  public void callSearchClient(Timer timer) {
    AppLocationTimerConfig appConfig = (AppLocationTimerConfig) timer.getInfo();
    searchClient.search(
      appConfig.getLocation(), appConfig.getApplicationName()
    );
  }

  @Schedule(minute = "13", hour = "2")
  public void closeAndTransferFiles() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Schedule(minute = "13", hour = "3")
  public void lzoFiles() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Schedule(minute = "11", hour = "2")
  public void dailyCountReport() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  @Schedule(minute = "13", hour = "4", dayOfMonth = "2")
  public void monthlyBackup() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}
