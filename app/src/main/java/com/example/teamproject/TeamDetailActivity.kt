import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.R
import com.example.teamproject.Team
import com.google.gson.Gson

class TeamDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        intent.getStringExtra("team")?.let { json ->
            val team = Gson().fromJson(json, Team::class.java)
            // TODO: team 객체로 UI 채우기
        }
    }
}
