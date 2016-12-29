/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to deal with Ip Addresses.
 *
 * Created by rsanchez on 16/11/16.
 */
public class IpAddressUtils {

  private IpAddressUtils() {}

  /**
   * Returns a set of ip address based on IP range defined according the CIDR notation
   * @param input IP range defined according the CIDR notation
   * @return Set of ip address
   */
  public static Set<String> getIpRange(String input) {
    if (StringUtils.isEmpty(input)) {
      throw new IllegalArgumentException("Address is not a valid IP address");
    }

    int index = input.indexOf("/");

    String address;
    int blocks = 0;

    if (index == -1) {
      address = input;
    } else {
      address = input.substring(0, index);
      blocks = Integer.parseInt(input.substring(index + 1));
    }

    if (!InetAddressValidator.getInstance().isValidInet4Address(address)) {
      throw new IllegalArgumentException("Address " + address + " is not a valid IP address");
    }

    if (blocks > 0) {
      Set<String> range = new HashSet<>();

      SubnetUtils utils = new SubnetUtils(input);
      utils.setInclusiveHostCount(true);

      Collections.addAll(range, utils.getInfo().getAllAddresses());

      return range;
    } else {
      return Collections.singleton(address);
    }

  }

  /**
   * Validates if the input is a set of ip address according the CIDR notation
   * @param input Input data to be validated
   * @return true if the input is a set of ip address according the CIDR notation or false otherwise
   */
  public static boolean isIpRange(String input) {
    if (StringUtils.isEmpty(input)) {
      return false;
    }

    int index = input.indexOf("/");
    String address;

    if (index == -1) {
      return false;
    } else {
      address = input.substring(0, index);
    }

    if (!InetAddressValidator.getInstance().isValidInet4Address(address)) {
      return false;
    }

    SubnetUtils utils = new SubnetUtils(input);
    utils.setInclusiveHostCount(true);

    String[] allAddresses = utils.getInfo().getAllAddresses();
    return allAddresses != null && allAddresses.length > 1;
  }
}
