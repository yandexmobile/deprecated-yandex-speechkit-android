<p align="center" >
  <img src="https://yastatic.net/doccenter/images/tech-ru/speechkit/freeze/Ts_YHKzyKst6Oe-VCHRciqug0c.png" alt="Yandex SpeechKit" title="Yandex SpeechKit Mobile SDK">
</p>

[![Main](https://tech.yandex.com/speechkit/mobilesdk/)
[![EULA](https://yandex.ru/legal/speechkit)
[![Documentation](https://tech.yandex.ru/speechkit/mobilesdk/doc/android/stable/ref/concepts/About-docpage/)

Use the SpeechKit library to integrate speech recognition, text-to-speech, music identification, and Yandex voice activation into your Android mobile app. Voice control makes your app more user-friendly, especially for people who use it on the go or whose hands are busy. Without touching the screen, users can activate the desired feature with a single phrase.

SpeechKit supports the following languages for speech recognition and text-to-speech:
- Russian
- English
- Ukrainian
- Turkish

> If the language you need isn't supported, SpeechKit allows you to switch to the Google speech recognition system.

If the number of voice commands accessing your application does not exceed 10,000 per day, you can use the basic version of the SpeechKit Mobile SDK for free. If you have more requests, you can either pay for the amount over the limit, or switch to the commercial pricing plan. The commercial plan removes the restrictions and adds extra functionality. For instance, you can create unique voices and speech models for specific contexts.

## Where to start

- [Find out what SpeechKit is](https://tech.yandex.com/speechkit/mobilesdk/), what functionality is available in the library, and how it can improve your application.
- Download [SpeechKit samples](https://github.com/yandexmobile/yandex-speechkit-samples-android) and try to run them. This is a good way to quickly learn how to use the main components of the library.
- Read the [Quick Start](https://tech.yandex.ru/speechkit/mobilesdk/doc/android/stable/quick-start/concepts/about-docpage/), which describes the configuration required for the library to work, along with recommended steps for fast and successful integration of SpeechKit in your application.
- Review the [Documentation](https://tech.yandex.ru/speechkit/mobilesdk/doc/android/stable/ref/concepts/About-docpage/) for the library's API: the main classes and their functions, features, and restrictions.
- If you have previously used SpeechKit, we recommend that you also read the [Guide for migrating to SpeechKit 3.12.2](https://tech.yandex.ru/speechkit/mobilesdk/doc/intro/overview/concepts/speechkit-overview-versions-docpage/#3-12-2), which explains what has changed since the latest available version.

## Before you get started

Before you start working with APIs of Yandex services, you must create an API key:
- Go to the [Developer Dashboard](https://developer.tech.yandex.ru/).
- Click `Get key`.
- Enter a name for the key and select `SpeechKit Mobile SDK` in the list.
- Enter your information and the type of project.
- Click `Submit`.

After the API key is generated, you will be redirected to the `Developer Dashboard`. Under `Your API keys`, you can see all the keys that you have created.

## Installation

You can use Maven to add SpeechKit to a project. To do this, open the `build.gradle` file in the project. Add the Maven Central repository to the list of available repositories. In the `repositories` section, type:
```bash
repositories {
    ...
    mavenCentral()
}
```

Open the `build.gradle` file for the application (module). In the `dependencies` section, add the dependency:
```bash
dependencies {
    ...
    compile 'com.yandex.android:speechkit:3.12.2'
}
```

In the `build.gradle` file for the application (module), go to the `defaultConfig` section and enter the minimum Android API version required by your application (14 or later):
```bash
defaultConfig {
        ...
        minSdkVersion 14
}
```

## Requirements

Minimum system requirements:
- SDK version 14
- Android Studio 2.3.3 +

## Architecture

### Global settings

* `SpeechKit`
  - `LocationProvider`
  - `EventLogger`
  - `LogLevel`

### Speech recognition

* `OnlineRecognizer`
  - `OnlineRecognizer.Builder`
    - `Language`
    - `OnlineModel`
    - `SoundFormat`
  - `Recognizer`
  - `RecognizerListener`
  - `Track`
    - `Artist`
  - `Recognition`
    - `RecognitionHypothesis`
      - `RecognitionWord`
    - `Biometry`
      - `BiometryGroup`
      - `BiometryEmotion`
      - `LanguageScore`
* `RecognizerActivity`
  - `AudioProcessingMode`
  - `RecognizerUIEarcons`
    - `RecognizerUIEarcons.Builder`
    - `DefaultEarconsBundle`

### Speech synthesis

* `OnlineVocalizer`
  - `OnlineVocalizer.Builder`
    - `Language`
    - `Voice`
    - `Emotion`
    - `SoundFormat`
    - `SoundQuality`
  - `Vocalizer`
    - `TextSynthesizingMode`
  - `VocalizerListener`
  - `Synthesis`
    - `SoundBuffer`

### Voice activation

* `PhraseSpotter`
  - `PhraseSpotter.Builder`
  - `PhraseSpotterListener`

### Additional functionality

* `AudioSource`
  - `AutoStartStopAudioSource`
  - `ManualStartStopAudioSource`
  - `AudioSourceListener`
* `UniProxySession`

## Usage

### `SpeechKit`

Singleton class for configuring and controlling the library. You don't need to explicitly create or destroy instances of the `SpeechKit` class. To access an object, use the `getInstance` method, which creates an instance of the` SpeechKit` class when accessed the first time. This instance is destroyed when the application closes.

Before using any of the SpeechKit functionality, you must configure `SpeechKit` using the API key (for more information, see [Before you get started](#before-you-get-started)).
```java
SpeechKit.getInstance().init(getApplicationContext(), "developer_api_key")
```

### `OnlineRecognizer`

```java
OnlineRecognizer recognizer = new OnlineRecognizer.Builder(Language.ENGLISH, OnlineModel.QUERIES, this)
                 .setDisableAntimat(false)
                 .setEnablePunctuation(true)
                 .build();
recognizer.prepare();
recognizer.startRecording();
```

```java
public void onPartialResults(@NonNull Recognizer recognizer, @NonNull Recognition recognition, boolean endOfUtterance) {
    System.out.println("Partial result: " + results);
    if (endOfUtterance) {
        System.out.println("Partial result: " + recognition.getRequestId());
    }
}
```

### `OnlineVocalizer`

```java
OnlineVocalizer.Builder vocalizer = new OnlineVocalizer.Builder(Language.ENGLISH, this)
                .setEmotion(Emotion.GOOD)
                .setVoice(Voice.ERMIL)
                .build();
vocalizer.prepare();
vocalizer.synthesize("What's up kid?", Vocalizer.TextSynthesizingMode.APPEND);
```

## Credits

The SpeechKit Mobile SDK is created and developed by [Yandex](https://yandex.com/company/). 

## License

The licensing terms for using SpeechKit are described in the [Terms of Use](https://yandex.ru/legal/speechkit/?lang=en).
If you do not agree to any of the terms described in the license agreement, you cannot use SpeechKit.

## Contacts

- If you have any **general questions** about the library, look for answers in the [FAQ](https://tech.yandex.com/speechkit/mobilesdk/doc/intro/faq/concepts/faq-docpage/).
- If you **found a bug in the library**, _and you can provide the exact steps for reproducing it or logs containing the necessary data_, write to us via the [feedback form](https://tech.yandex.com/speechkit/mobilesdk/doc/intro/feedback/concepts/troubleshooting-docpage/).
- If you **need help**, contact us at speechkit@support.yandex.ru.
- If you need to increase the maximum number of requests for speech recognition and/or speech synthesis, send an inquiry to speechkit@support.yandex.ru.