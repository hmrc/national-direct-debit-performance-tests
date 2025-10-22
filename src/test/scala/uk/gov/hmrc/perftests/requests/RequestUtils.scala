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
import uk.gov.hmrc.perftests.requests.SetupDDRequests.baseUrlFor

import java.io.InputStream
import java.time.LocalDate
import scala.util.Random

trait RequestUtils {

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
  val paymentPlan: String        = "/payment-plan-type"

  val authLoginStub: String = baseUrlFor("auth-login-stub")
  val authLoginStubUrl      = s"$authLoginStub/auth-login-stub/gg-sign-in"
  val CsrfPattern           = """<input type="hidden" name="csrfToken" value="([^"]+)""""
  val UpscanUrlPattern      = """<form action="([^"]+)" method="POST""""

  // Test Data
  val name: String          = "Teddy Dickson"
  val sortCode: String      = "207102"
  val accountNumber: String = "44311655"

  val ctPaymentRef: String   = "8337018376A00108A"
  val nicPaymentRef: String  = "600340016213526259"
  val olPaymentRef: String   = "XG000001000188"
  val vatPaymentRef: String  = "562235945"
  val sdltPaymentRef: String = "100000511MX"
  val payePaymentRef: String = "961PX0023480X"
  val mgdPaymentRef: String  = "XVM00005554321"
  val saPaymentRef: String   = "5829820384K"
  val tcPaymentRef: String   = "WT447571311207NE"

  def saveCsrfToken(): CheckBuilder[RegexCheckType, String] = regex(_ => CsrfPattern).saveAs("csrfToken")

  def saveUpscanUrl(): CheckBuilder[RegexCheckType, String] = regex(_ => UpscanUrlPattern).saveAs("upscanUrl")

  def getRandomDateWithin30Days(): (String, String, String) = {
    val today = LocalDate.now()
    val randomDays = Random.nextInt(30) + 1 // Between 1 and 30 days
    val futureDate = today.plusDays(randomDays)

    val day   = f"${futureDate.getDayOfMonth}%02d"
    val month = f"${futureDate.getMonthValue}%02d"
    val year  = futureDate.getYear.toString

    (day, month, year)
  }
}
