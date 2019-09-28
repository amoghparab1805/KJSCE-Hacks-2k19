from django.shortcuts import render
import pyrebase

from rest_framework.views import APIView
from django.http import HttpResponse, JsonResponse

from .models import *

config = {
    'apiKey': "AIzaSyDUP81oZRkZJS_TRYDnXelp9_TWNCj2OWA",
    'authDomain': "kjscehack-fa5ae.firebaseapp.com",
    'databaseURL': "https://kjscehack-fa5ae.firebaseio.com",
    'projectId': "kjscehack-fa5ae",
    'storageBucket': "",
    'messagingSenderId': "803546715173",
    'appId': "1:803546715173:web:ec618fcdabd348a278dbcc",
    'measurementId': "G-H4SLLNZ59N"
}

firebase = pyrebase.initialize_app(config)
auth = firebase.auth()

# data
# {
# "displayName": "Harsh Mistry",
# "email": "mistryharsh28@gmail.com",
# "phoneNumber": null,
# "photoURL": "https://lh3.googleusercontent.com/a-/AAuE7mDqfpYfLfWXLOZcwO4vuNTJQ6ydxp00iRLvHYZTPQ",
# "providerId": "google.com"
# }

class UpdateUserData(APIView):
    
    def post(self, request, *args, **kwargs):
        # import pdb; pdb.set_trace()
        data = request.data
        print(request)
        print(data)
        display_name = data['displayName']
        email = data['email']
        phone_number = data['phoneNumber']
        photo_url = data['photoURL']
        provided_id = data['providerId']

        if(provided_id == 'google.com'):
            try:
                social_user = SocialUser.objects.get(email=email, provided_id=provided_id)
            except SocialUser.DoesNotExist:
                social_user = SocialUser(
                    display_name=display_name, 
                    email=email,
                    phone_number=phone_number,
                    photo_url=photo_url,
                    provided_id=provided_id
                )
                social_user.save()
        elif(provided_id == 'phone'):
            try:
                social_user = SocialUser.objects.get(phone_number=phone_number, provided_id=provided_id)
            except SocialUser.DoesNotExist:
                social_user = SocialUser(
                    display_name=display_name, 
                    email=email,
                    phone_number=phone_number,
                    photo_url=photo_url,
                    provided_id=provided_id
                )
                social_user.save()

        return JsonResponse({
            'Success': 'Success',
            'social_user': social_user.display_name # TODO : Make serializer for user
        })
