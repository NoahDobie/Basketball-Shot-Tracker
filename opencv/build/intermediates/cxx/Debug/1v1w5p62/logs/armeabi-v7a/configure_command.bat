@echo off
"C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=24" ^
  "-DANDROID_PLATFORM=android-24" ^
  "-DANDROID_ABI=armeabi-v7a" ^
  "-DCMAKE_ANDROID_ARCH_ABI=armeabi-v7a" ^
  "-DANDROID_NDK=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\Noah's MSI\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\build\\intermediates\\cxx\\Debug\\1v1w5p62\\obj\\armeabi-v7a" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\build\\intermediates\\cxx\\Debug\\1v1w5p62\\obj\\armeabi-v7a" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BC:\\Users\\Noah's MSI\\AndroidStudioProjects\\Basketball-Shot-Tracker\\opencv\\.cxx\\Debug\\1v1w5p62\\armeabi-v7a" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"