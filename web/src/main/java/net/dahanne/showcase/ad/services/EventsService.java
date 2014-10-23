package net.dahanne.showcase.ad.services;

import net.dahanne.showcase.ad.pojos.Event;
import net.dahanne.showcase.ad.pojos.Result;
import net.dahanne.showcase.persistence.DataAccessException;

/**
 * Created by anthony on 2014-10-23.
 */
public interface EventsService {
  Result processSubscriptionOrder(Event event);

  Result processSubscriptionCancel(Event event);

  Result processSubscriptionChangeEvent(Event event);

  Result processUserAssignmentEvent(Event event);

  Result processUserUnAssignmentEvent(Event event);
}
