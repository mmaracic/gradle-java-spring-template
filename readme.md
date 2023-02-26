# Readme

* multimodule spring application
* Contain one spring application and one spring library
* Convention plugins define dependencies, gradle plugins and properties shared between modules
  * there are two convention plugins - application-conventions.gradle and common-conventions.gradle
  * convention plugins can not contain versions
  * all versions including spring version are listed in the build.gradle that builds convention plugins (in buildSrc folder) and maintained in global gradle.properties
  * gradle properties is not in the same folder as build.gradle which needs it so it is imported through custom properties object
* common-conventions.gradle defines dependencies, gradle plugins and properties shared between all modules - libraries and applications
  * Spring dependency management plugin enforces the spring version and is common to all modules but exact version is defined by org.springframework.boot dependency in build.gradle in buildSrc folder 
  * common-conventions cannot include org.springframework.boot plugin because that would force all modules to be applications but as a dependency (in build.gradle in buildSrc folder) it defines the versions
  * all modules need to include common-conventions.gradle - applications and libraries
* application-conventions.gradle defines dependencies, gradle plugins and properties needed only for applications
  * org.springframework.boot plugin is explicitly needed only in application to enable building bootable jar - bootJar task
  * only applications need to include application-conventions.gradle
