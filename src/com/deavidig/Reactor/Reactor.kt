package com.deavidig.Reactor

import kotlin.system.exitProcess

object Reactor {

	private const val ANSI_RESET: String = "\u001B[0m";
	private const val ANSI_RED: String = "\u001B[31m";
	private const val ANSI_GREEN: String = "\u001B[32m";
	private const val ANSI_YELLOW: String = "\u001B[33m";
	private const val ANSI_CYAN: String = "\u001B[36m";

	enum class ReactorErrorType(val message : String) {
		IllegalArgumentValue("Illegal Argument Type"),
		IncompatibleArgumentValue("Incompatible Argument Type"),
		FailedInitializeValue("Failed Initialize Value")
	}

	enum class ReactorInfoType(val message : String) {
		ArgumentValue("Argument Type"),
	}

	fun setReactorError(errorType: ReactorErrorType, message: String): Nothing {
		println("$ANSI_RED[Reactor.JReactor - 1.0] ${errorType.message} (Instruction Error): $message$ANSI_RESET")
		exitProcess(1)
	}

	fun setReactorError(errorType: Array<ReactorErrorType>, message: String): Nothing {
		println("$ANSI_RED[Reactor.JReactor - 1.0] ${errorType.joinToString(" & ") { it.message }} (Instruction Error): $message$ANSI_RESET")
		exitProcess(1)
	}

	fun setReactorInfo(infoType: ReactorInfoType, message: String): Unit {
		println("$ANSI_CYAN[Reactor.JReactor - 1.0] ${infoType.message} (Instruction Info): $message$ANSI_RESET")
		return Unit
	}
}
