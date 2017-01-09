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

package org.symphonyoss.integration.model.yaml;

import org.apache.commons.lang3.StringUtils;
import org.symphonyoss.integration.utils.IpAddressUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Holds information about which origins can communicate with the Integration Bridge.
 * Created by rsanchez on 18/11/16.
 */
public class WhiteList {

  /**
   * Retrieves the whitelist based on the allowed origins configured on YAML file.
   *
   * The whitelist may have the origin host name, IP address or both. The IP address can be a
   * range using CIDR notation.
   *
   * YAML configuration:
   *
   * <pre>
   *  {@code
   *    allowed_origins:
   *      - host: squid-104-1.sc1.uc-inf.net
   *      - address: 192.30.252.40
   *      - host: ec2-107-23-104-115.compute-1.amazonaws.com
   *        address: 107.23.104.115
   *      - address: 192.30.252.0/22
   *  }
   * </pre>
   *
   * You must include a character '-' for each new entry in the list.
   *
   * In this sample, the first entry has only the host name and the second has only the IP address.
   * The third entry has the IP address and the host name. The last entry has an IP address range
   * using CIDR notation.
   *
   * @param allowedOrigins Allowed origins
   */
  public Set<String> getWhiteList(List<AllowedOrigin> allowedOrigins) {
    final Set<String> whiteList = new HashSet<>();

    if (allowedOrigins != null) {
      for (AllowedOrigin origin : allowedOrigins) {
        String host = origin.getHost();
        String address = origin.getAddress();

        if (!StringUtils.isEmpty(host)) {
          whiteList.add(host);
        }

        if (!StringUtils.isEmpty(address)) {
          whiteList.addAll(IpAddressUtils.getIpRange(address));
        }
      }
    }

    return whiteList;
  }

}
