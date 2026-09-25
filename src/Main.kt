import com.deavidig.Reactor.application.Application
import com.deavidig.Reactor.application.Window
import com.deavidig.Reactor.content.Settings
import com.deavidig.Reactor.graphics.Color
import com.deavidig.Reactor.graphics.scale.Percentage
import com.deavidig.Reactor.graphics.scale.Pixel

object Main {
	@JvmStatic
	fun main(args: Array<String>) {
		MyApplication()
	}
}

class MyApplication() : Application() {
	override fun onStart() {
		setWindowRun {
			MyWindow(application = this@MyApplication)
		}
	}
}

class MyWindow(application: Application) : Window(application = application) {
	override fun onCreate() {
		super.onCreate()
		setWidth(Pixel(800))
		setHeight(Pixel(800))
		setY(Percentage(45))

		println(getSettings().getSystemMode())
		getSettings().setSystemMode(Settings.SYSTEM_MODE_THEME_DARK)
		println(getSettings().getSystemMode())
	}
}