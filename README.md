# Simple localization

[![Maven Central](https://img.shields.io/maven-central/v/io.github.edmondantes/simple-localization?color=green&style=flat-square)](https://search.maven.org/search?q=g:io.github.edmondantes%20a:simple-localization)
![GitHub](https://img.shields.io/github/license/edmondantes/simple-localization?color=green&style=flat-square)
![Lines of code](https://img.shields.io/tokei/lines/github/edmondantes/simple-localization?style=flat-square)

---

### Lightweight library for localize your application

# Table of content

1. [How to add this library to your project](#how-to-add-this-library-to-your-project)
2. [Getting started](#getting-started)
3. [Type of localizers](#type-of-localizers)
   1. [InMemoryLocalizer](#inmemorylocalizer)
   2. [ResourceBundleLocalizer](#resourcebundlelocalizer)
4. [Localized string format](#localization-arguments)
5. [Features](#features)
   1. [Localization arguments](#localization-arguments)
   2. [Localization request](#localization-request)
   3. [Localization context](#localization-context)

## How to add this library to your project

### Maven

```xml

<dependency>
    <groupId>io.github.edmondantes</groupId>
    <artifactId>simple-localization</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle (kotlin)

```kotlin
implementation("io.github.edmondantes:simple-localization:0.1.0")
```

### Gradle (groove)

```groovy
implementation "io.github.edmondantes:simple-localization:0.1.0"
```

## Getting started

For start using this library you should create an object of class `Localizer`. For default you can use one of two type
of `Localizer`: `InMemoryLocalizer` and `ResourceBundleLocalizer`. 

Above you can see example with `InMemoryLocalizer`

```kotlin
val supportLanguages = listOf("language1", "language2")
val defaultLanguage = "language2"
val localizations = mapOf(
    "language1" to mapOf("key1" to "localizedStr1"),
    "language2" to mapOf("key1" to "localizedStr2")
)

val localizer = InMemoryLocalizer(supportLanguages, defaultLanguage, localizations)

val localizedStr1 = localizer.localize("language1", localizationKey("key1"))
```
## Type of localizers

### InMemoryLocalizer

`InMemoryLocalizer` using variables for localization.

You can create `InMemoryLocalizer` with these arguments:
1. List of supports languages names
2. Default language's name
3. Map that associate languages' name and map that associate string key with localized string

### ResourceBundleLocalizer

`ResourceBundleLocalizer` using `ResourceBundle` for localization

You can create `ResourceBundleLocalizer` if you have `ResourceBundle` in your classpath. 
`ResourceBundleLocalizer` requires these arguments:
1. List of supports languages tags
2. Default language's tag 
3. Path to `ResourceBundle` in classpath and files' prefix. 
(example: `localization/lang` will correspond files with mask `localization/lang_*`)

## Localized string format
In Localization string you can use any characters, but you should escape characters: `{` and `}`

## Features
### Localization arguments
You can provide arguments for localized string:
```kotlin
localizer.localize("language", localizationKey("key", "arg1", "arg2"))
```
or 
```kotlin
localizer.localize("language", localizationKey("key", listOf("arg1", "arg2")))
```

For using argument in localized string you should write `{}` or `{<index>}`.
### Localization request
You can mix localized string and not localized string. 
For that you can use `LocalizationRequest` interface and `DefaultLocalizationRequest` implementation.
```kotlin
localizer.localize("language", localizationRequest {
   add("key", "arg1", "arg2")
   withoutLocalization("string without localization")
   add("key2", "arg3")
})
```

### Localization context
You can use `LocalizationContext` for localization. 
This interface represents `Localizer` for defined language, 
so you shouldn't specify language for every localization method.
You can get `LocalizationContext` from `Localizer`:
```kotlin
val localizationContext = localizer.getContext("language")
```

