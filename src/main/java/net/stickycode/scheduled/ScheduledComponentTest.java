/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.scheduled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;

import org.junit.Test;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.configured.Configuration;
import net.stickycode.configured.ConfigurationRepository;
import net.stickycode.configured.ConfigurationSystem;

public class ScheduledComponentTest {

  protected void configure(ScheduleTestObject instance) {
    StickyBootstrap.crank(this, getClass()).inject(instance);
  }

  @Inject
  ConfigurationRepository configurations;

  @Inject
  ConfigurationSystem system;

  @Inject
  ScheduledRunnableRepository schedules;

  @Test
  public void verifyScheduling()
      throws InterruptedException {

    ScheduleTestObject instance = new ScheduleTestObject();
    configure(instance);

    assertThat(system)
        .as("Implementors must inject(this) so that the configuration system can be configured")
        .isNotNull();

    system.start();

    assertThat(configurations).hasSize(4);
    Configuration c = configurations.iterator().next();
    assertThat(c).hasSize(2);

    ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
    for (ScheduledRunnable runnable : schedules) {
      Schedule s = runnable.getSchedule();
      if (s.isEnabled())
        pool.scheduleAtFixedRate(runnable, s.getInitialDelay(), s.getPeriod(), s.getUnits());
    }
    Thread.sleep(1000);
    assertThat(instance.counter.get()).isGreaterThan(1);
    assertThat(instance.failCounter.get()).isEqualTo(1);
    assertThat(instance.retryCounter.get()).isGreaterThan(1);
  }
}