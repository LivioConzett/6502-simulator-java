/*
Copyright (c) 2023, Livio Conzett
All rights reserved.

This source code is licensed under the BSD-style license found in the
LICENSE file in the root directory of this source tree.
*/

package tech.livio.java6502;

/**
 * Callback for the compiler status change
 */
public interface CompilerCallBack {
    public void run(CompilerStatus compilerStatus);

}
