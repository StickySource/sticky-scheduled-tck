package net.stickycode.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class FakeConfigurationSource
    implements ConfigurationSource {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void apply(ConfigurationKey key, ResolvedConfiguration values) {
    log.debug("key {}", key.join("."));
    if (key.join(".").contains("scheduleTestObject.runIt.schedule"))
      values.add(new ConfigurationValue() {

        @Override
        public boolean hasPrecedence(ConfigurationValue v) {
          return true;
        }

        @Override
        public String get() {
          return "every 2 seconds";
        }
      });
  }
}
