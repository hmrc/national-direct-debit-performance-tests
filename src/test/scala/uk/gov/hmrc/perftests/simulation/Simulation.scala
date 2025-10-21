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
import uk.gov.hmrc.perftests.requests.Requests._

class Simulation extends PerformanceTestRunner {

  setup("setup-direct-debit-journey-ct", "Setup Direct Debit Journey With CT Payment Plan") withRequests
    (
      authLogIn,
      navigateToYourDDIPage,
      navigateToSetupDDPage,
      navigateToSelectAccountPage,
      submitAccountType,
      navigateToBankAccountPage,
      enterBankAccountDetailsPage,
      navigateToBankDetailsCYAPage,
      submitBankDetails,
      navigateToAuthorityConfirmPage,
      submitAuthorityConfirmation,
      navigateToPaymentOptionPage,
      choosePaymentOption,
      navigateToPaymentReferencePage,
      enterPaymentReference,
      navigateToPaymentAmountPage,
      enterPaymentAmount,
      navigateToPaymentDatePage,
      enterPaymentDate,
      navigateToDDCYAPage,
      submitDDDetails,
      navigateToDDConfirmationPage
    )
  runSimulation()
}
