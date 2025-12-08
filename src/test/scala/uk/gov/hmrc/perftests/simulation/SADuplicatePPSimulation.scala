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
import uk.gov.hmrc.perftests.requests.AmendPaymentPlanRequests._
import uk.gov.hmrc.perftests.requests.AuthLoginRequests.{authLogIn, navigateToAuth}
import uk.gov.hmrc.perftests.requests.BankDetailsCYARequests.{navigateToBankDetailsCYAPage, submitBankDetails}
import uk.gov.hmrc.perftests.requests.BankDetailsRequests.{enterBankAccountDetails, navigateToBankAccountPage}
import uk.gov.hmrc.perftests.requests.ConfirmAuthorityRequests.{navigateToAuthorityConfirmPage, submitAuthorityConfirmation}
import uk.gov.hmrc.perftests.requests.PaymentAmountRequests.{enterRegularPaymentAmount, navigateToRegularPaymentAmountPage}
import uk.gov.hmrc.perftests.requests.PaymentCYARequests.{navigateToDDCYAPage, submitDDDetails}
import uk.gov.hmrc.perftests.requests.PaymentConfirmationRequests.navigateToDDConfirmationPage
import uk.gov.hmrc.perftests.requests.PaymentPlanRequests.{addPaymentPlanEndDate, choosePaymentPlan, enterPaymentPlanEndDate, enterPaymentPlanStartDate, landOnSABudgetPPDetailsPage, navigateToAddPaymentPlanEndDate, navigateToBudgetPaymentPlanEndDatePage, navigateToPaymentPlanPage, navigateToPaymentPlanStartDatePage, redirectToSABudgetPPDetailsPage, redirectToSetUpANewPPPage}
import uk.gov.hmrc.perftests.requests.PaymentReferenceRequests.{enterPaymentRefNumber, navigateToPaymentReferencePage}
import uk.gov.hmrc.perftests.requests.PaymentTypeRequests.{choosePaymentOption, navigateToPaymentOptionPage}
import uk.gov.hmrc.perftests.requests.SelectAccountTypeRequests.{navigateToSelectAccountPage, submitAccountType}
import uk.gov.hmrc.perftests.requests.SelectPaymentFrequencyRequests.{navigateToPaymentFrequencyPage, selectFrequency}
import uk.gov.hmrc.perftests.requests.SetupDDRequests.{accountNumber, mgdPaymentRef, name, navigateToSetupDDPage, navigateToYourDDIPage, saPaymentRef, sortCode}

trait SADuplicatePPSimulation {
  this: PerformanceTestRunner =>
  setup("duplicate-payment-plan-journey-DW1", "DW1-Duplicate Payment Plan Journey") withRequests
    (
      navigateToAuth,authLogIn("0000000009000205"),
      navigateToYourDDIPage,
      redirectToSABudgetPPDetailsPage,
      landOnSABudgetPPDetailsPage,
      redirectToSetUpANewPPPage,
      navigateToPaymentOptionPage,choosePaymentOption("sa"),
      navigateToPaymentPlanPage, choosePaymentPlan("budgetPaymentPlan"),
      navigateToPaymentReferencePage, enterPaymentRefNumber(saPaymentRef),
      navigateToPaymentFrequencyPage, selectFrequency("monthly"),
      navigateToRegularPaymentAmountPage, enterRegularPaymentAmount,
      navigateToPaymentPlanStartDatePage, enterPaymentPlanStartDate,
      navigateToAddPaymentPlanEndDate, addPaymentPlanEndDate,
      navigateToBudgetPaymentPlanEndDatePage,enterPaymentPlanEndDate,
      navigateToDDCYAPage, submitDDDetails,
      navigateToExistingPPQpage,submitExistingPPDetailS,
      navigateToDDConfirmationPage
    )
  setup("duplicate-payment-plan-journey-DW2", "DW2-Duplicate Payment Plan Journey") withRequests
    (
      navigateToAuth,authLogIn("0000000009000204"),
      navigateToYourDDIPage,
      redirectToSABudgetPPDetailsPage,
      landOnSABudgetPPDetailsPage,
      redirectToSetUpANewPPPage,
      navigateToPaymentOptionPage,choosePaymentOption("mgd"),
      navigateToPaymentPlanPage, choosePaymentPlan("variablePaymentPlan"),
      navigateToPaymentReferencePage, enterPaymentRefNumber(mgdPaymentRef),
      navigateToPaymentPlanStartDatePage, enterPaymentPlanStartDate,
      navigateToDDCYAPage, submitDDDetails,
      navigateToDW2Page
    )
}
