        // 스피너 < >
        lateinit var repeat_scadule : String        
        val repeat_Scadule_spinner : Spinner = findViewById(R.id.spinnerRepeat)
        repeat_Scadule_spinner.adapter = ArrayAdapter.createFromResource(this,R.array.repeat_Scadule, android.R.layout.simple_spinner_item)

        repeat_Scadule_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                    when(position){
                    0 -> repeat_scadule = "없음"
                    1 -> repeat_scadule = "매일"
                    2 -> repeat_scadule = "매주"
                    3 -> repeat_scadule = "매달"
                }
            }
        }
        
        //스피너2 <성별 선택>
        var spinner_Gender = findViewById<Spinner>(R.id.spinner_Gender)

        spinner_Gender.adapter = ArrayAdapter.createFromResource(this, R.array.genderArray, android.R.layout.simple_spinner_item)

        spinner_Gender.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> pets_Gender = "선택안함"
                    1 -> pets_Gender = "남성"
                    2 -> pets_Gender = "여성"

                }
            }
        }

