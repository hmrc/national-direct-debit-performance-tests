/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object ConfirmAuthorityRequests extends ServicesConfiguration with RequestUtils {

  val navigateToAuthorityConfirmPage: HttpRequestBuilder =
    http("Confirm the authority")
      .get(s"$baseUrl$redirectUrl$confirmAuthority")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Confirm your authority"))

  def submitAuthorityConfirmation(authOption: String): HttpRequestBuilder =
    http("Confirm the authority")
      .post(s"$baseUrl$redirectUrl$confirmAuthority")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", authOption)
      .check(status.is(303))
}
