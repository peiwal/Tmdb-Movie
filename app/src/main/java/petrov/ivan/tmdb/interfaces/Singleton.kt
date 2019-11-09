package petrov.ivan.tmdb.interfaces

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope


@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class Singleton