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

  val baseUrl: String            = baseUrlFor("ndds-frontend")
  val redirectUrl: String        = "/direct-debits"
  val yourDDPayment: String      = "/your-direct-debit-payment"
  val setupDDPayment: String     = "/set-up-direct-debit-payment"
  val selectAccountType: String  = "/personal-or-business-account"
  val bankAccountPage: String    = "/your-bank-details"
  val confirmBankDetails: String = "/confirm-bank-details"
  val confirmAuthority: String   = "/confirm-authority"
  val choosePayment: String      = "/where-are-you-making-a-payment"
  val paymentReference: String   = "/payment-reference"
  val paymentAmount: String      = "/payment-amount"
  val paymentDate: String        = "/payment-date"
  val savedDDPayment: String     = "/your-saved-direct-debit-payment"
  val ddSubmission: String       = "/direct-debit-payment-submitted"

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
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Is this a personal or business account?"))

  val submitAccountType: HttpRequestBuilder =
    http("Navigate to Bank Account page")
      .post(s"$baseUrl$redirectUrl$selectAccountType")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "personal")
      .check(status.is(303))

  val navigateToBankAccountPage: HttpRequestBuilder =
    http("Navigate to Bank Account page")
      .get(s"$baseUrl$redirectUrl$bankAccountPage")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("What are your bank details?"))

  val enterBankAccountDetailsPage: HttpRequestBuilder =
    http("Enter bank account details page")
      .post(s"$baseUrl$redirectUrl$bankAccountPage")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("accountHolderName", "Teddy Dickson")
      .formParam("sortCode", "207102")
      .formParam("accountNumber", "44311655")
      .check(status.is(303))

  val navigateToBankDetailsCYAPage: HttpRequestBuilder =
    http("Submit bank account details")
      .get(s"$baseUrl$redirectUrl$confirmBankDetails")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Check your answers"))

  val submitBankDetails: HttpRequestBuilder =
    http("Submit bank account details")
      .post(s"$baseUrl$redirectUrl$confirmBankDetails")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val navigateToAuthorityConfirmPage: HttpRequestBuilder =
    http("Confirm the authority")
      .get(s"$baseUrl$redirectUrl$confirmAuthority")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Confirm your authority"))

  val submitAuthorityConfirmation: HttpRequestBuilder =
    http("Confirm the authority")
      .post(s"$baseUrl$redirectUrl$confirmAuthority")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "yes")
      .check(status.is(303))

  val navigateToPaymentOptionPage: HttpRequestBuilder =
    http("Navigate to payment option page")
      .get(s"$baseUrl$redirectUrl$choosePayment")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Where do you need to make direct debit payment?"))

  val choosePaymentOption: HttpRequestBuilder =
    http("Choose the payment Option")
      .post(s"$baseUrl$redirectUrl$choosePayment")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "ct")
      .check(status.is(303))

  val navigateToPaymentReferencePage: HttpRequestBuilder =
    http("Navigate to payment reference page")
      .get(s"$baseUrl$redirectUrl$paymentReference")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("What is your payment reference"))

  val enterPaymentReference: HttpRequestBuilder =
    http("Enter Payment Reference number")
      .post(s"$baseUrl$redirectUrl$paymentReference")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "8337018376A00108A")
      .check(status.is(303))

  val navigateToPaymentAmountPage: HttpRequestBuilder =
    http("Navigate to payment amount page")
      .get(s"$baseUrl$redirectUrl$paymentAmount")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("What amount do you want to pay?"))

  val enterPaymentAmount: HttpRequestBuilder =
    http("Enter Payment amount")
      .post(s"$baseUrl$redirectUrl$paymentAmount")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "1000")
      .check(status.is(303))

  val navigateToPaymentDatePage: HttpRequestBuilder =
    http("Navigate to payment date page")
      .get(s"$baseUrl$redirectUrl$paymentDate")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("What date are you making this payment?"))

  val enterPaymentDate: HttpRequestBuilder =
    http("Enter payment date")
      .post(s"$baseUrl$redirectUrl$paymentDate")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value.day", "10")
      .formParam("value.month", "10")
      .formParam("value.year", "2026")
      .check(status.is(303))

  val navigateToDDCYAPage: HttpRequestBuilder =
    http("Navigate to Direct Debit CYA Page")
      .get(s"$baseUrl$redirectUrl$savedDDPayment")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Check your answers"))

  val submitDDDetails: HttpRequestBuilder =
    http("Submit DD Payment details")
      .post(s"$baseUrl$redirectUrl$savedDDPayment")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val navigateToDDConfirmationPage: HttpRequestBuilder =
    http("Navigate to DD payment details confirmation page")
      .get(s"$baseUrl$redirectUrl$ddSubmission")
      .check(status.is(200))
      .check(regex("Your direct debit payment was successful"))
}
