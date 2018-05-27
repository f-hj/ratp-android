package fr.fruitice.trome.feature

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.app.Fragment
import android.support.design.widget.Snackbar
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.trome.feature.Objects.otp.PlanResult
import fr.fruitice.trome.feature.R.layout.activity_main
import fr.fruitice.trome.feature.R.layout.fragment_itinerary
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.fragment_itinerary.*
import kotlinx.android.synthetic.main.fragment_itinerary.view.*

class ItineraryFragment : Fragment() {

    var codeFrom: String? = null
    var codeTo: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) return
        if (data == null) return

        val tv: TextView

        if (requestCode == 0) {
            tv = fromText
            codeFrom = data.getStringExtra("id")
        } else if (requestCode == 1) {
            tv = toText
            codeTo = data.getStringExtra("id")
        } else return

        tv.setTextAppearance(R.style.TextAppearance_AppCompat_Body1)
        tv.text = SpannableStringBuilder(data.getStringExtra("name") + " (" + data.getStringExtra("id") + ")")

        if (codeFrom != null && codeTo != null) {
            Log.d("PlanRequest", "https://otp.trome.app/otp/routers/default/plan?fromPlace=1:$codeFrom&toPlace=1:$codeTo&mode=TRAM%2CRAIL%2CSUBWAY%2CFUNICULAR%2CGONDOLA%2CWALK")
            "https://otp.trome.app/otp/routers/default/plan?fromPlace=1:$codeFrom&toPlace=1:$codeTo&mode=TRAM%2CRAIL%2CSUBWAY%2CFUNICULAR%2CGONDOLA%2CWALK".httpGet().responseObject(PlanResult.Deserializer()) { _, _, result ->
                Log.d("res", "getted")
                Log.d("res", result.toString())

                val (res, err) = result

                if (err != null) {
                    Log.d("PlanErr", err.response.responseMessage)
                    return@responseObject
                }

                Log.d("PlanResult", "nbItin: " + res?.plan?.itineraries?.size)
                var itinText = ""
                res?.plan?.itineraries?.forEach {
                    itinText += "start: ${it.startTime}, end: ${it.endTime}, transitTime: ${it.transitTime}\n"
                    it.legs.forEach {
                        itinText += "    ${it.mode}: from ${it.from.name} to ${it.to.name} for ${it.endTime - it.startTime}"
                        if (it.route.isNotEmpty()) { itinText += " by ${it.route}" }
                        itinText += "\n"
                    }
                }

                text_itinerary.text = itinText
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_itinerary, container, false)!!

        view.fromLayout.setOnClickListener({
            Log.d("clickForm", "yep")
            val intent = Intent(context, SearchActivity::class.java)
            startActivityForResult(intent, 0)
        })

        view.toLayout.setOnClickListener({
            val intent = Intent(context, SearchActivity::class.java)
            startActivityForResult(intent, 1)
        })

        return view
    }

}