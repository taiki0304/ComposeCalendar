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
**TODO**

## License
**TODO**