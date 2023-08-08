# Expressions 

Note: Apk is attached in files list

Test Cases:

1. Open the app and you will see 2 tabs: Home and History
2. In home tab enter expressions in the edit text and click on submit
3. You will see toast message that expression submited.
4. Now move to history tab and click on fetch history button.
5. You will see the list of expression submited and answer from api call

Logic/Code flow

1. I have created 2 fragments of Home and History
2. In home fragemnt there is code added for entering the expressions in Edittext and submiting those expressions by pressing submit button
3. After submit button is pressed, it will give a call to the API which will evaluate expression in an Async Task and return the result from the Api Call in onPostExecute method
4. After result is obtained, data(both input and result) will be stored in Database using SqLite. Table is created with fields input, result and date. Insert data operation will be done in a new background thread as it is a network operation so we don't want main thread to be overloaded.
5. Now when user moves to History tab they will see a button to fetch data
6. Once button is pressed, list of expression data stored in database will be displayed in ascending order of time in which they were stored. 
