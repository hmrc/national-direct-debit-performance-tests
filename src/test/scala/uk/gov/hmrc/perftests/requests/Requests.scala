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
import io.gatling.core.check.CheckBuilder
import io.gatling.core.check.regex.RegexCheckType
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object Requests extends ServicesConfiguration {

  val baseUrl: String           = baseUrlFor("ndds-frontend")
  val redirectUrl: String       = "/direct-debits"
  val yourDDPayment: String     = "/your-direct-debit-payment"
  val setupDDPayment: String    = "/set-up-direct-debit-payment"
  val selectAccountType: String = "/personal-or-business-account"
  val bankAccountPage: String   = "/your-bank-details"

  val authLoginStub: String = baseUrlFor("auth-login-stub")
  val authLoginStubUrl      = s"$authLoginStub/auth-login-stub/gg-sign-in"
  val CsrfPattern           = """<input type="hidden" name="csrfToken" value="([^"]+)""""
  val UpscanUrlPattern      = """<form action="([^"]+)" method="POST""""

  def saveCsrfToken(): CheckBuilder[RegexCheckType, String] = regex(_ => CsrfPattern).saveAs("csrfToken")

  def saveUpscanUrl(): CheckBuilder[RegexCheckType, String] = regex(_ => UpscanUrlPattern).saveAs("upscanUrl")

  lazy val navigateToAuth: HttpRequestBuilder =
    http("Auth wizard")
      .get(Requests.authLoginStubUrl)
      .check(status.is(200))
      .check(regex("Authority Wizard").exists)

  val authLogIn: HttpRequestBuilder =
    http("Login as an GG sign-in")
      .post(Requests.authLoginStubUrl)
      .formParam("redirectionUrl", redirectUrl)
      .formParam("credentialStrength", "strong")
      .formParam("authorityId", "")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Individual")
      .check(status.is(303))
      .check(header("Location").is(redirectUrl))

  val navigateToYourDDIPage: HttpRequestBuilder =
    http("Navigate to Your Direct Debit page")
      .get(s"$baseUrl$redirectUrl$yourDDPayment")
      .check(status.is(200))
      .check(regex("Your Direct Debit instructions"))

  val navigateToSetupDDPage: HttpRequestBuilder =
    http("Navigate to Setup Direct Debit page")
      .get(s"$baseUrl$redirectUrl$setupDDPayment")
      .check(status.is(200))
      .check(regex("Set up a Direct Debit payment"))

  val navigateToSelectAccountPage: HttpRequestBuilder =
    http("Navigate to Select Account type page")
      .get(s"$baseUrl$redirectUrl$selectAccountType")
      .check(status.is(200))
      .check(regex("Is this a personal or business account?"))

}
