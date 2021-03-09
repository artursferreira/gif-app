package com.artur.giphyapp.home.adapter

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.artur.giphyapp.R
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.databinding.FragmentMenuBinding
import com.artur.giphyapp.utils.sendNotification
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.android.inject
import java.io.File


/**
 * Created by artur on 04/08/2018.
 */
class MenuFragment : BottomSheetDialogFragment(), NavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val downloadManager: DownloadManager by inject()
    private val notificationManager: NotificationManager by inject()

    private var gifItem: GifItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        gifItem = arguments?.get(KEY_GIF) as GifItem?

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        binding.navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.download) {
            gifItem?.let {
                val fileName = it.id + ".gif"
                val request =
                    DownloadManager.Request(Uri.parse(it.gifUrl))
                        .setTitle(fileName)
                        .setDescription("Downloading gif...")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            fileName
                        )
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)

                downloadManager.enqueue(request)
            }
        } else if (item.itemId == R.id.share) {
            gifItem?.let {
                val share = Intent(Intent.ACTION_SEND)

                share.type = "image/gif"

                val fileName = it.id + ".gif"
                val imageFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName
                )

                val uri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().packageName + ".provider",
                    imageFile
                )
                share.putExtra(Intent.EXTRA_STREAM, uri)

                startActivity(Intent.createChooser(share, "Share GIF"))
            }
        }

        dismiss()
        return false
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val downloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (downloadId == -1L) return

            // query download status

            // query download status
            val cursor: Cursor =
                downloadManager.query(downloadId?.let { DownloadManager.Query().setFilterById(it) })
            if (cursor.moveToFirst()) {
                val status: Int =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                val fileName = gifItem?.id + ".gif"
                notificationManager.sendNotification(
                    context!!,
                    notificationManager,
                    File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        fileName
                    )
                )

            } else {
                // download is cancelled
            }
        }
    }

    companion object {
        const val KEY_GIF = "gif"
    }
}