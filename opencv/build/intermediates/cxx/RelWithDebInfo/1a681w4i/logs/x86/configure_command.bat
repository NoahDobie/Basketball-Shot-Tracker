@echo off
"C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=24" ^
  "-DANDROID_PLATFORM=android-24" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\build\\intermediates\\cxx\\RelWithDebInfo\\1a681w4i\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\build\\intermediates\\cxx\\RelWithDebInfo\\1a681w4i\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BC:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\.cxx\\RelWithDebInfo\\1a681w4i\\x86" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"