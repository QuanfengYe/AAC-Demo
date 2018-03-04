package me.yeqf.android.library.brvah.bean

import com.chad.library.adapter.base.entity.SectionEntity
import me.yeqf.android.persistence.entity.GankIoCache

/**
 * Created by yeqf on 2018/3/3.
 */
class GankIoSection : SectionEntity<GankIoCache> {

    constructor(isHeader: Boolean, name: String): super(isHeader, name)
    constructor(data: GankIoCache): super(data)

}