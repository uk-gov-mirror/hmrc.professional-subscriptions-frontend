/*
 * Copyright 2019 HM Revenue & Customs
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

package generators

import models.TaxYearSelection
import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary
import pages._
import play.api.libs.json.{JsValue, Json}

trait UserAnswersEntryGenerators extends PageGenerators with ModelGenerators {

  implicit lazy val arbitraryRemoveSubscriptionUserAnswersEntry: Arbitrary[(RemoveSubscriptionPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[RemoveSubscriptionPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryWhichSubscriptionUserAnswersEntry: Arbitrary[(WhichSubscriptionPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[WhichSubscriptionPage.type]
        value <- arbitrary[String].suchThat(_.nonEmpty).map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryEmployerContributionUserAnswersEntry: Arbitrary[(EmployerContributionPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[EmployerContributionPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryYourEmployerUserAnswersEntry: Arbitrary[(YourEmployerPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[YourEmployerPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryYourAddressUserAnswersEntry: Arbitrary[(YourAddressPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[YourAddressPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryTaxYearSelectionUserAnswersEntry: Arbitrary[(TaxYearSelectionPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[TaxYearSelectionPage.type]
        value <- arbitrary[TaxYearSelection].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitrarySameAmountAllYearsUserAnswersEntry: Arbitrary[(SameAmountAllYearsPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[SameAmountAllYearsPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitrarySubscriptionAmountUserAnswersEntry: Arbitrary[(SubscriptionAmountPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[SubscriptionAmountPage.type]
        value <- arbitrary[Int].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryExpensesEmployerPaidUserAnswersEntry: Arbitrary[(ExpensesEmployerPaidPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[ExpensesEmployerPaidPage.type]
        value <- arbitrary[Int].map(Json.toJson(_))
      } yield (page, value)
    }

}
