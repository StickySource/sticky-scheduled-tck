package net.stickycode.scheduled;

import net.stickycode.configuration.ConfigurationKey;
import net.stickycode.configuration.ConfigurationSource;
import net.stickycode.configuration.ConfigurationValue;
import net.stickycode.configuration.ResolvedConfiguration;
import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class FakeConfigurationSource
    implements ConfigurationSource {

  @Override
  public void apply(ConfigurationKey key, ResolvedConfiguration values) {
    for (String k : key.join(".")) {
      if (k.startsWith("scheduleTestObject."))
        values.add(new ConfigurationValue() {

          @Override
          public boolean hasPrecedence(ConfigurationValue v) {
            return true;
          }

          @Override
          public String get() {
            return "every 500 milliseconds";
          }
        });
    }
  }
}
