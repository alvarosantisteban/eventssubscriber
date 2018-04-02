package com.karlmax.eventsubscriber

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_organizers.*

/**
 * Manages the  list of organizers, allowing deleting them or adding several at the same time.
 *
 * TODO Allow removing organizers
 */
class OrganizersActivity : AppCompatActivity() {


    companion object {
        @JvmField val TAG: String = OrganizersActivity::class.java.simpleName
    }

    private lateinit var organizersListAdapter: OrganizersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizers)

        // Show the Up button in the action bar.
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            val dialogView = layoutInflater.inflate(R.layout.subscribe_dialog, null, false)
            val builder = AlertDialog.Builder(this)

            builder.setView(dialogView)
                    .setPositiveButton(R.string.organizer_subscriber_subscribe_button, { dialog, id ->
                        // TODO Search in Fb API if there is an ID for the provided string.
                        // If yes, add the ID as Organizer, If no, display error message

                        val editText = dialogView.findViewById<View>(R.id.dialogOrganizer) as EditText
                        val organizersName = editText.text.toString()
                        val persistor = OrganizerKeyPersistor()
                        persistor.persistKey(this, organizersName)
                        updateOrganizersList()
                    }).show()
        }

        organizersList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        organizersListAdapter = OrganizersListAdapter()
        organizersList.adapter = organizersListAdapter
        organizersList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        updateOrganizersList()
    }

    private fun updateOrganizersList() {
        val persistor = OrganizerKeyPersistor()

        if (!persistor.isEmpty(this)) {
            // Empty the adapter before getting the new events
            organizersListAdapter.items.clear()

            val organizers = persistor.getPersistedKeys(this)
            for (organizer: String in organizers) {
                ModelProvider.provideOrganizeBasicInfo(organizer) {
                    organizersListAdapter.items.add(it)

                    organizersListAdapter.notifyDataSetChanged()
                }
            }
        } else {
            Log.d(TAG, "There are no organizers -> The list is empty")
        }
    }
}
