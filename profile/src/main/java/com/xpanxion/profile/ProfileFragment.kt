package com.xpanxion.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xpanxion.architecture.*

private const val ID_KEY = "ID_KEY"
private const val NAME_KEY = "NAME_KEY"
private const val ROLE_KEY = "ROLE_KEY"
private const val SKILLS_KEY = "SKILLS_KEY"
private const val STARRED_KEY = "STARRED_KEY"
private const val LOCATION_KEY = "LOCATION_KEY"
private const val AVAILABILITY_KEY = "AVAILABILITY_KEY"
private const val LOCATION_IMAGE_KEY = "LOCATION_IMAGE_KEY"
private const val PHONE_NUMBER_KEY = "PHONE_NUMBER_KEY"
private const val EMAIL_KEY = "EMAIL_KEY"

class ProfileFragment : TitledBackHandlerFragment() {

    private var id: Long?

    init {
        title = "Profile"
        id = arguments?.getLong(ID_KEY)
    }

    companion object {
        private lateinit var person : Person
        fun newInstance(person: Person): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putLong(ID_KEY, person.id)
            args.putBoolean(STARRED_KEY, person.starred)
            args.putString(NAME_KEY, person.name.toString())
            args.putString(ROLE_KEY, person.role.toString())
            args.putParcelableArray(SKILLS_KEY, person.skills)
            args.putInt(LOCATION_IMAGE_KEY, person.location.icon)
            args.putString(LOCATION_KEY, person.location.toString())
            args.putFloatArray(AVAILABILITY_KEY, person.availability.rawData.toFloatArray())
            args.putString(PHONE_NUMBER_KEY, person.phoneNumber)
            args.putString(EMAIL_KEY, person.email)
            fragment.arguments = args
            this.person = person
            return fragment
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.findViewById<TextView>(R.id.person_profile_name).text =
                arguments?.getString(NAME_KEY)
        view.findViewById<TextView>(R.id.person_profile_role).text =
                arguments?.getString(ROLE_KEY)
        view.findViewById<TextView>(R.id.person_profile_location).text =
                arguments?.getString(LOCATION_KEY)
        arguments?.getInt(LOCATION_IMAGE_KEY)?.let { image ->
            view.findViewById<ImageView>(R.id.profile_location_image_view)
                    .setImageDrawable(resources.getDrawable(image))
        }
        view.findViewById<AvailabilityGraph>(R.id.profile_availability_graph).let { graph ->
            graph.maximum = 100f
            graph.months = listOf("May", "June", "July", "August", "September", "October")
            arguments?.getFloatArray(AVAILABILITY_KEY)?.toTypedArray()?.let { data ->
                graph.availability = data
            }
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.profile_skills_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        arguments?.getParcelableArray(SKILLS_KEY).let { skills ->
            recyclerView.adapter = SkillItemRecyclerViewAdapter(skills as Array<Skill>)
        }
        val star = view.findViewById<ImageView>(R.id.profile_star_button)
        setStar(star,arguments?.getBoolean(STARRED_KEY)!!)
        star.setOnClickListener{
            arguments?.let{arguments ->
                val currentStarValue = arguments.getBoolean(STARRED_KEY)
                setStar(star,!currentStarValue)
                arguments.putBoolean(STARRED_KEY, !currentStarValue)
            }
        }

        val phoneButton = view.findViewById<ImageView>(R.id.profile_call_button)
            phoneButton.setImageResource(if(arguments?.getString(PHONE_NUMBER_KEY).equals("") || arguments?.getString(PHONE_NUMBER_KEY).equals(null))0 else R.drawable.call_icon)
            phoneButton.setOnClickListener { makePhoneCall() }

        val emailButton = view.findViewById<ImageView>(R.id.profile_mail_button)
            emailButton.setImageResource(if(arguments?.getString(EMAIL_KEY).equals("") || arguments?.getString(EMAIL_KEY).equals(null) )0 else R.drawable.mail_icon)
            emailButton.setOnClickListener { makeEmail() }
        return view
    }

    override fun onBackPressed() = false

    private fun setStar(star : ImageView, starred : Boolean){
      star.setImageResource(if(starred)R.drawable.star_checked else R.drawable.star_unchecked)
    }

    private fun makePhoneCall(){
        var callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+arguments?.getString(PHONE_NUMBER_KEY)))
        startActivity(callIntent)
    }

    private fun makeEmail(){
        var emailIntent = Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:" + arguments?.getString(EMAIL_KEY)))
        startActivity(emailIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        person.starred = arguments?.getBoolean(STARRED_KEY)!!
    }
}
