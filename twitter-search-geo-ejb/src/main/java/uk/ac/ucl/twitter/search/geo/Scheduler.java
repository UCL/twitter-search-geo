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
    searchClient.runSearch(
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
