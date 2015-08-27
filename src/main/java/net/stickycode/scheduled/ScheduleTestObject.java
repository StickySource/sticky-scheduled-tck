/**
 * Copyright (c) 2011 RedEngine Ltd, http://www.RedEngine.co.nz. All rights reserved.
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

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.stickycode.exception.TransientException;
import net.stickycode.stereotype.scheduled.Scheduled;

public class ScheduleTestObject {

  private Logger log = LoggerFactory.getLogger(getClass());

  AtomicInteger counter = new AtomicInteger(0);

  AtomicInteger failCounter = new AtomicInteger(0);

  AtomicInteger retryCounter = new AtomicInteger(0);

  @Scheduled
  public void runIt() {
    log.info("runIt {}", counter.incrementAndGet());
  }

  @Scheduled
  public void fail() {
    log.info("fail {}", failCounter.incrementAndGet());
    throw new RuntimeException();
  }

  @Scheduled
  public void retry() {
    log.info("retry {}", retryCounter.incrementAndGet());
    throw new TransientException("oops:");
  }
}