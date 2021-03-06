package oth.archaeologicalfieldwork.views.editsite

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import oth.archaeologicalfieldwork.R
import oth.archaeologicalfieldwork.helpers.showImagePicker
import oth.archaeologicalfieldwork.models.sites.Location
import oth.archaeologicalfieldwork.models.sites.SiteModel
import oth.archaeologicalfieldwork.views.*

class AddOrEditSitePresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

    var site = SiteModel()

    var edit = false

    init {
        if (view.intent.hasExtra("site_edit")) {
            site = view.intent.extras.getParcelable("site_edit")
            view.showSiteInformation(site)
            edit = true
            info("site-edit opened")
        }
    }

    fun doAddOrSaveSite(site: SiteModel) {
        if (site.title.isEmpty()) {
            Toast.makeText(view, R.string.message_enter_site_title, Toast.LENGTH_SHORT)
                .show() // having issue with anko.toast(), using the traditional way here
        } else {
            if (edit) {
                app.sites?.update(site)
                val resultIntent = Intent()
                resultIntent.putExtra("changed_site", site)
                view?.setResult(Activity.RESULT_OK, resultIntent)
            } else {
                app.sites?.create(site)
            }
            view?.finish()
        }
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", site.location)
    }

    fun doSelectImage() {
        showImagePicker(view!!, IMAGE_REQUEST)
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                site.images.add(data.data.toString())
                view?.displaySiteImages(site)
                info("Image_Request activity result, data: $data")
            }
            LOCATION_REQUEST -> {
                if (data.hasExtra("location")) {
                    site.location = data.extras.getParcelable<Location>("location")
                    view?.updateLocation(site.location)
                }
                info("Location Request activity result, data: $site.location")
            }
        }
    }

}