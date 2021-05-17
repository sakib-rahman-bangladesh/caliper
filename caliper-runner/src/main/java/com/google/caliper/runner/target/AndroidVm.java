/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.caliper.runner.target;

import com.google.auto.value.AutoValue;
import com.google.caliper.runner.config.VmConfig;
import com.google.caliper.runner.config.VmType;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/** An Android VM, e.g. Dalvik or ART. */
@AutoValue
public abstract class AndroidVm extends Vm {

  /** Creates a new {@link AndroidVm} for the given configuration. */
  public static AndroidVm create(VmConfig config, String classpath, String nativeLibraryPath) {
    return new AutoValue_AndroidVm(VmType.ANDROID, config, classpath, nativeLibraryPath);
  }

  AndroidVm() {}

  @Override
  public String executable() {
    return config().executable().or("dalvikvm");
  }

  @Override
  public ImmutableSet<String> trialArgs() {
    return ImmutableSet.of();
  }

  @Override
  public ImmutableList<String> lastArgs() {
    // app_process expects a "command directory" argument; use the bin directory where the binary is
    return executable().equals("app_process")
        ? ImmutableList.of("/system/bin")
        : ImmutableList.of();
  }

  /** Returns the native library path that should be used for workers on this VM. */
  public abstract String nativeLibraryPath();

  @Override
  public ImmutableList<String> classpathArgs() {
    // Unlike -cp <classpath>, this works for both dalvikvm and app_process
    return ImmutableList.of(
        "-Djava.class.path=" + classpath(), "-Djava.library.path=" + nativeLibraryPath());
  }

  @Override
  public Predicate<String> vmPropertiesToRetain() {
    return Predicates.alwaysFalse();
  }
}
