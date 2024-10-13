// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.tcp

public sealed interface TcpError {
  public data object ConnectionTimeout : TcpError

  public class Unknown(public val cause: Throwable) : TcpError
}
