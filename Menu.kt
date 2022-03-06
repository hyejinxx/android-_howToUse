import android.view.Menu
import android.view.MenuItem

//메뉴 1
(menu/add_scadule_menu.xml) 
<menu>
    <item
        android:id="@+id/ok"
        android:checkable="false"
        android:icon="@drawable/paw_5892565_1280"
        android:title="확인"
        android:visible="true"
        app:showAsAction="always"></item>
    <item
        android:id="@+id/cancel"
        android:title="취소"
        android:icon="@drawable/paw_5892565_1280"
        app:showAsAction="always"></item>
    </menu>

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_scadule_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cancel
            -> {
                val intentBack = Intent(this, MainActivity::class.java)
                startActivity(intentBack)
            }
            R.id.ok->{
                addScadule()
                val intentBack = Intent(this, MainActivity::class.java)
                startActivity(intentBack)
            }
        }
        return super.onOptionsItemSelected(item)
    } 
    
//메뉴2
(menu/main_menu.xml)
<menu>
    <item
        android:id="@+id/scaduale_management"
        android:title="일정관리"
        android:icon="@drawable/paw_5892565_1280"
        app:showAsAction="always">
    </item>
    <item
        android:id="@+id/diary"
        android:title="한줄일기"
        android:icon="@drawable/paw_5892565_1280"
        app:showAsAction="always"></item>
    <item
        android:id="@+id/pet_page"
        android:title="펫페이지"
        android:icon="@drawable/paw_5892565_1280"
        app:showAsAction="always"
        ></item>
</menu>
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var showCalender : ImageButton = findViewById(R.id.calender_btn)
        val transaction = supportFragmentManager.beginTransaction()
        showCalender.visibility = View.VISIBLE
        when(item.itemId){
            R.id.scaduale_management -> {
                transaction.replace(R.id.mainfragment, MainPost())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.diary->{
                transaction.replace(R.id.mainfragment, Diary())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.pet_page-> {
                showCalender.visibility = View.GONE //pet_page 누를때만 
                transaction.replace(R.id.mainfragment, PetPage())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return super.onOptionsItemSelected(item)

    }
