package hash.application.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hash.application.R
import kotlinx.android.synthetic.main.fragment2.*
import android.widget.Toast
import hash.application.activities.SearchActivity
import hash.application.dataType.SearchCoarse
import hash.application.managers.IngredientManager
import hash.application.managers.WebManager

//"suggestion" fragment
class Fragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment2, container, false)
    }

    override fun onStart() {
        val btnSearch = button
        // set on-click listener for search with conditions
        btnSearch.setOnClickListener {
            Toast.makeText(context, "You clicked on search.", Toast.LENGTH_SHORT).show()
            lateinit var str: String
            // get recipes from server using WebManager
            val webThread = Thread(Runnable {
                val notHaving = ArrayList<String>()
                if(checkBox2.isChecked){
                    notHaving.add("egg")
                }
                if(checkBox3.isChecked){
                    notHaving.add("fish")
                }
                if(checkBox4.isChecked){
                    notHaving.add("milk")
                }
                if(checkBox5.isChecked){
                    notHaving.add("peanut")
                }
                if(checkBox6.isChecked){
                    notHaving.add("shellfish")
                }
                if(checkBox7.isChecked){
                    notHaving.add("soy")
                }
                if(checkBox8.isChecked){
                    notHaving.add("nuts")
                }
                if(checkBox9.isChecked){
                    notHaving.add("wheat")
                }
                val tmp = SearchCoarse(editText.text.toString().toInt(), IngredientManager.getNameList(), notHaving)
                str = WebManager.searchCoarse(tmp)
            })
            webThread.start()
            // call SearchActivity to process returned string from WebManager
            val intent = Intent(context, SearchActivity::class.java)
            webThread.join()
            intent.putExtra("json", str)
            startActivity(intent)
        }
        super.onStart()
    }
}