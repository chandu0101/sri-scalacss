

enablePlugins(ScalaJSPlugin)

name := "sri-scalacss"

version := "2016.11.0"

organization := "com.github.chandu0101"

scalaVersion := "2.11.8"

val scalatestVersion = "3.0.0"

crossScalaVersions := Seq("2.11.8","2.12.0")


libraryDependencies += "com.github.japgolly.scalacss" %%% "core" % "0.5.0"
libraryDependencies += "com.github.chandu0101" %%% "sri-web" % "0.6.0"


relativeSourceMaps := true

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature",
  "-language:postfixOps", "-language:implicitConversions",
  "-language:higherKinds", "-language:existentials")

//======================== Publication Settings =========================\\

homepage := Some(url("https://github.com/chandu0101/sri-scalacss"))
licenses +=("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomExtra :=
  <scm>
    <connection>scm:git:github.com:chandu0101/sri-scalacss</connection>
    <developerConnection>scm:git:git@github.com:chandu0101/sri-scalacss.git</developerConnection>
    <url>github.com:chandu0101/sri-scalacss.git</url>
  </scm>
    <developers>
      <developer>
        <id>chandu0101</id>
        <name>Chandra Sekhar Kode</name>
      </developer>
    </developers>


//================ Testing settings =====================\\
libraryDependencies += "org.scalatest" %%% "scalatest" % scalatestVersion % Test

scalaJSStage in Global := FastOptStage

requiresDOM := true

jsDependencies += RuntimeDOM

jsEnv in Test := new PhantomJS2Env(scalaJSPhantomJSClassLoader.value, addArgs = Seq("--web-security=no"))
//jsEnv in Test := NodeJSEnv().value
//postLinkJSEnv in Test := NodeJSEnv().value
