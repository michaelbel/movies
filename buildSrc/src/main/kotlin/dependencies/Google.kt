@file:Suppress("SpellCheckingInspection", "unused")

package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api

/**
 * Google Play services, Play Core
 *
 * @see <a href="https://developers.google.com/android/guides/setup">Set up Google Play services</a>
 * @see <a href="https://d.android.com/reference/com/google/android/play/core/release-notes">Play Core</a>
 */

private const val GmsAds = "20.5.0"
private const val GmsAdsIdentifier = "18.0.1"
private const val GmsAdsLite = "20.5.0"
private const val GmsAfsNative = "19.0.3"
private const val GmsAnalytics = "18.0.1"
private const val GmsAppset = "16.0.2"
private const val GmsAuth = "20.0.1"
private const val GmsAuthApiPhone = "18.0.1"
private const val GmsAuthBlockstore = "16.1.0"
private const val GmsAwareness = "19.0.1"
private const val GmsBase = "18.0.1"
private const val GmsBasement = "18.0.0"
private const val GmsCast = "21.0.1"
private const val GmsCastFramework = "21.0.1"
private const val GmsCronet = "18.0.1"
private const val GmsFido = "19.0.0-beta"
private const val GmsFitness = "21.0.1"
private const val GmsGames = "22.0.1"
private const val GmsInstantapps = "18.0.1"
private const val GmsLocation = "19.0.1"
private const val GmsMaps = "18.0.2"
private const val GmsMlkitBarcodeScanning = "17.0.0"
private const val GmsMlkitFaceDetection = "16.2.1"
private const val GmsMlkitImageLabeling = "16.0.6"
private const val GmsMlkitImageLabelingCustom = "16.0.0-beta2"
private const val GmsMlkitLanguageId = "16.0.0-beta2"
private const val GmsMlkitTextRecognition = "17.0.1"
private const val GmsNearby = "18.0.2"
private const val GmsOssLicenses = "17.0.0"
private const val GmsPasswordComplexity = "18.0.1"
private const val GmsPay = "16.0.3"
private const val GmsRecaptcha = "17.0.1"
private const val GmsSafetynet = "18.0.1"
private const val GmsTagManager = "18.0.1"
private const val GmsTasks = "18.0.1"
private const val GmsVision = "20.1.3"
private const val GmsWallet = "19.1.0"
private const val GmsWearable = "17.1.0"

private const val GoogleServicesPluginVersion = "4.3.10"
private const val OssLicensesPluginVersion = "0.10.4"
private const val StrictPluginVersion = "1.2.2"

private const val PlayCoreVersion = "1.8.1"
private const val PlayCoreReviewVersion = "2.0.0"
private const val PlayCoreUpdateVersion = "2.0.0"

private const val Ads = "com.google.android.gms:play-services-ads:$GmsAds"
private const val AdsIdentifier = "com.google.android.gms:play-services-ads-identifier:$GmsAdsIdentifier"
private const val AdsLite = "com.google.android.gms:play-services-ads-lite:$GmsAdsLite"
private const val AfsNative = "com.google.android.gms:play-services-afs-native:$GmsAfsNative"
private const val Analytics = "com.google.android.gms:play-services-analytics:$GmsAnalytics"
private const val Appset = "com.google.android.gms:play-services-appset:$GmsAppset"
private const val Auth = "com.google.android.gms:play-services-auth:$GmsAuth"
private const val AuthApiPhone = "com.google.android.gms:play-services-auth-api-phone:$GmsAuthApiPhone"
private const val AuthBlockstore = "com.google.android.gms:play-services-auth-blockstore:$GmsAuthBlockstore"
private const val Awareness = "com.google.android.gms:play-services-awareness:$GmsAwareness"
private const val Base = "com.google.android.gms:play-services-base:$GmsBase"
private const val Basement = "com.google.android.gms:play-services-basement:$GmsBasement"
private const val Cast = "com.google.android.gms:play-services-cast:$GmsCast"
private const val CastFramework = "com.google.android.gms:play-services-cast-framework:$GmsCastFramework"
private const val Cronet = "com.google.android.gms:play-services-cronet:$GmsCronet"
private const val Fido = "com.google.android.gms:play-services-fido:$GmsFido"
private const val Fitness = "com.google.android.gms:play-services-fitness:$GmsFitness"
private const val Games = "com.google.android.gms:play-services-games:$GmsGames"
private const val InstantApps = "com.google.android.gms:play-services-instantapps:$GmsInstantapps"
private const val Location = "com.google.android.gms:play-services-location:$GmsLocation"
private const val Maps = "com.google.android.gms:play-services-maps:$GmsMaps"
private const val MlkitBarcodeScanning = "com.google.android.gms:play-services-mlkit-barcode-scanning:$GmsMlkitBarcodeScanning"
private const val MlkitFaceDetection = "com.google.android.gms:play-services-mlkit-face-detection:$GmsMlkitFaceDetection"
private const val MlkitImageLabelling = "com.google.android.gms:play-services-mlkit-image-labeling:$GmsMlkitImageLabeling"
private const val MlkitImageLabellingCustom = "com.google.android.gms:play-services-mlkit-image-labeling-custom:$GmsMlkitImageLabelingCustom"
private const val MlkitLanguageId = "com.google.android.gms:play-services-mlkit-language-id:$GmsMlkitLanguageId"
private const val MlkitTextRecognition = "com.google.android.gms:play-services-mlkit-text-recognition:$GmsMlkitTextRecognition"
private const val Nearby = "com.google.android.gms:play-services-nearby:$GmsNearby"
private const val OssLicenses = "com.google.android.gms:play-services-oss-licenses:$GmsOssLicenses"
private const val PasswordComplexity = "com.google.android.gms:play-services-password-complexity:$GmsPasswordComplexity"
private const val Pay = "com.google.android.gms:play-services-pay:$GmsPay"
private const val Recaptcha = "com.google.android.gms:play-services-recaptcha:$GmsRecaptcha"
private const val Safetynet = "com.google.android.gms:play-services-safetynet:$GmsSafetynet"
private const val Tagmanager = "com.google.android.gms:play-services-tagmanager:$GmsTagManager"
private const val Tasks = "com.google.android.gms:play-services-tasks:$GmsTasks"
private const val Vision = "com.google.android.gms:play-services-vision:$GmsVision"
private const val Wallet = "com.google.android.gms:play-services-wallet:$GmsWallet"
private const val Wearable = "com.google.android.gms:play-services-wearable:$GmsWearable"

const val GoogleServicesPlugin = "com.google.gms:google-services:$GoogleServicesPluginVersion"

private const val PlayCore = "com.google.android.play:core-ktx:$PlayCoreVersion"
private const val PlayCoreReview = "com.google.android.play:review-ktx:$PlayCoreReviewVersion"
private const val PlayCoreUpdate = "com.google.android.play:app-update-ktx:$PlayCoreUpdateVersion"

fun DependencyHandler.apiGooglePlayServicesAdsDependencies() {
    api(Ads)
}

fun DependencyHandler.apiPlayCoreDependencies() {
    api(PlayCore)
}