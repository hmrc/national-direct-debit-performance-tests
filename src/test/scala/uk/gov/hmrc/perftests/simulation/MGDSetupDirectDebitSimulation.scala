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

package uk.gov.hmrc.perftests.simulation

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.requests.AuthLoginRequests.authLogIn
import uk.gov.hmrc.perftests.requests.BankDetailsCYARequests.{navigateToBankDetailsCYAPage, submitBankDetails}
import uk.gov.hmrc.perftests.requests.BankDetailsRequests.{enterBankAccountDetails, navigateToBankAccountPage}
import uk.gov.hmrc.perftests.requests.ConfirmAuthorityRequests.{navigateToAuthorityConfirmPage, submitAuthorityConfirmation}
import uk.gov.hmrc.perftests.requests.PaymentAmountRequests.{enterPaymentAmount, navigateToPaymentAmountPage}
import uk.gov.hmrc.perftests.requests.PaymentCYARequests.{navigateToDDCYAPage, submitDDDetails}
import uk.gov.hmrc.perftests.requests.PaymentConfirmationRequests.navigateToDDConfirmationPage
import uk.gov.hmrc.perftests.requests.PaymentDateRequests.{enterPaymentDate, navigateToPaymentDatePage}
import uk.gov.hmrc.perftests.requests.PaymentPlanRequests.{choosePaymentPlan, navigateToPaymentPlanPage}
import uk.gov.hmrc.perftests.requests.PaymentReferenceRequests.{enterPaymentRefNumber, navigateToPaymentReferencePage}
import uk.gov.hmrc.perftests.requests.PaymentTypeRequests.{choosePaymentOption, navigateToPaymentOptionPage}
import uk.gov.hmrc.perftests.requests.SelectAccountTypeRequests.{navigateToSelectAccountPage, submitAccountType}
import uk.gov.hmrc.perftests.requests.SetupDDRequests._

trait MGDSetupDirectDebitSimulation {
  this: PerformanceTestRunner =>
  setup("setup-direct-debit-journey-mgd-singlePPlan", "MGD-Single Payment Plan-Setup DD Journey") withRequests
    (
      authLogIn,
      navigateToYourDDIPage,
      navigateToSetupDDPage,
      navigateToSelectAccountPage, submitAccountType,
      navigateToBankAccountPage, enterBankAccountDetails(name, sortCode, accountNumber),
      navigateToBankDetailsCYAPage, submitBankDetails,
      navigateToAuthorityConfirmPage, submitAuthorityConfirmation("yes"),
      navigateToPaymentOptionPage, choosePaymentOption("mgd"),
      navigateToPaymentPlanPage, choosePaymentPlan("singlePaymentPlan"),
      navigateToPaymentReferencePage, enterPaymentRefNumber(mgdPaymentRef),
      navigateToPaymentAmountPage, enterPaymentAmount,
      navigateToPaymentDatePage, enterPaymentDate,
      navigateToDDCYAPage, submitDDDetails,
      navigateToDDConfirmationPage
    )

  setup("setup-direct-debit-journey-mgd-variablePPlan", "MGD-Variable Payment Plan-Setup DD Journey") withRequests
    (
      authLogIn,
      navigateToYourDDIPage,
      navigateToSetupDDPage,
      navigateToSelectAccountPage, submitAccountType,
      navigateToBankAccountPage, enterBankAccountDetails(name, sortCode, accountNumber),
      navigateToBankDetailsCYAPage, submitBankDetails,
      navigateToAuthorityConfirmPage, submitAuthorityConfirmation("yes"),
      navigateToPaymentOptionPage, choosePaymentOption("mgd"),
      navigateToPaymentPlanPage, choosePaymentPlan("variablePaymentPlan"),
      navigateToPaymentReferencePage, enterPaymentRefNumber(mgdPaymentRef),
      navigateToPaymentAmountPage, enterPaymentAmount,
      navigateToPaymentDatePage, enterPaymentDate,
      navigateToDDCYAPage, submitDDDetails,
      navigateToDDConfirmationPage
    )
}
