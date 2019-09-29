from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework import generics, viewsets, permissions, status
from django.http import HttpResponse, JsonResponse
from django.contrib.auth.hashers import make_password
from rest_framework.response import Response

from .models import *
from .serializers import *

class SignUp(APIView):
    def post(self, request, *args, **kwargs):
        data = request.data
        username = data['uid']
        display_name = data['displayName']
        email = data['email']
        phone_number = data['phoneNumber']
        photo_url = data['photoURL']
        provider_id = data['providerId']
        import pdb; pdb.set_trace()
        first_name = display_name.split(' ')[0]
        last_name = display_name.split(' ')[1]
        try:
            user = User.objects.get(username=username)
        except User.DoesNotExist:
            user = User(
                username=username,
                first_name=first_name, 
                last_name=last_name,
                email=email,
                phone_number=phone_number,
                photo_url=photo_url,
                provider_id=provider_id
            )
            user.save()
        return JsonResponse({
            'Success': 'Success',
            'user': user.first_name # TODO : Make serializer for user
        })
