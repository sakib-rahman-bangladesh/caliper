/*
 * Copyright (C) 2013 Google Inc.
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

package com.google.caliper.bridge;

/**
 * A message sent from the runner to the worker to indicate whether or not measuring should 
 * continue.
 */
public class ShouldContinueMessage {
  private final boolean shouldContinue;

  public ShouldContinueMessage(boolean shouldContinue) {
    this.shouldContinue = shouldContinue;
  }
  
  public boolean shouldContinue() {
    return shouldContinue;
  }
  
  @Override public int hashCode() {
    return Boolean.valueOf(shouldContinue).hashCode();
  }

  @Override public boolean equals(Object obj) {
    return obj instanceof ShouldContinueMessage 
        && shouldContinue == ((ShouldContinueMessage) obj).shouldContinue;
  }
}
