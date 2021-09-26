# Compose Calendar
Calendar library made by [Jetpack Compose](https://developer.android.com/jetpack/compose)

## Set up
1. Make `github.properties` and add `.gitignore`
```
gpr.user={userName}
gpr.key={GithubAccessToken}
```

2. Add settings at `build.gradle`.
```groovy
def githubProperties = new Properties()
githubProperties.load(new FileInputStream(rootProject.file("github.properties")))

allprojects {
    repositories {
        ...
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/taiki0304/ComposeCalendar")
            credentials {
                username = githubProperties['gpr.user'] ?: System.getenv("USERNAME")
                password = githubProperties['gpr.key'] ?: System.getenv("TOKEN")
            }
        }
    }
}
```

3. Add `compose-calendar` and `accompanist-pager` dependency to `app/build.gradle`.
```groovy
plugins {
  id 'maven-publish'
}
dependencies {
  implementation "com.taiki0304:compose-calendar:<version>"
  implementation "com.google.accompanist:accompanist-pager:<version>"
}
```

## How to use
1. In your composable function, you can use `Calendar()`.
```kotlin
import com.monet.library.Calendar
...
@Composable
private fun CalendarScreen() {
  Calendar()
}
```

2. If you wanna customize property of calendar, you should change property of `CalendarManager`
   - `CalendarManager` is singleton class, so I suggest to customize property in your `Application` class.

## License
**TODO**
