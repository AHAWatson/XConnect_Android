package com.xpanxion.architecture

enum class Location(val city: String, val region: String, val icon: Int) {
    AMES("Ames", "Iowa", R.drawable.ames),
    PUNE("Pune", "India", R.drawable.pune),
    REMOTE("Remote", "",R.drawable.remote),
    KEARNEY("Kearney", "Nebraska",R.drawable.kearney),
    LINCOLN("Lincoln", "Nebraska",R.drawable.lincoln),
    ATLANTA("Atlanta", "Georgia",R.drawable.alpharetta),
    MANHATTAN("Manhattan", "Kansas",R.drawable.manhattan),
    FORT_COLLINS("Fort Collins", "Colorado",R.drawable.fort_collins);

    override fun toString(): String {
        return if(region.isNotEmpty())"$city, $region" else "$city"
    }
}

