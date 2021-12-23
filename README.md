# B07ProjectGroup4
CSCB07 Summer 2021 Project at University Of Toronto Scarborough

Project Requirements:
- Database of doctors: each doctor has a name, a gender (in case patients are searching for gender-specific doctors), weekly availabilities (which should change when patients book appointments), specializations (for patients looking for a specialist), and a list of patients who have visited them so far.
- Database of patients: each patient will have a name, a gender, date of birth, a list of previous appointments, a list of upcoming appointments, and a list of doctors at the clinic that they have previously seen.
- Login page for patients, and one for doctors.
- When patients log in they should see their upcoming appointments as well as a "book appointment" button. When they click that button, they should see a list of doctors. There is a filter option for filtering on doctors' gender and specialization. When they select a doctor, they should see the doctor's availabilities for the upcoming week. They should be able to select a timeslot and book the appointment. This action should update their upcoming appointments as well as the doctor's upcoming appointments lists.
- When doctors log in, they should see their upcoming appointments too. They should be able to inspect the patients and see their info, along with a list of previous doctors this patient has seen. They should also be able to view their own schedule and see which patients have booked when, as well as which time slots are still available.
- When an appointment time passes, the patients' upcoming and past appointments as well as the list of doctors they have seen should be updated accordingly, and the doctor's upcoming appointments and patients list should be updated. 
- The program must be implemented such that if any doctor leaves the clinic or new doctors join, it should be easy to include them in the scheduling system. Also, when new patients sign up to use the app, it should be easy to include them as well.
